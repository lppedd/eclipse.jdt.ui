/*******************************************************************************
 * Copyright (c) 2020 IBM Corporation and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.jdt.ui.tests.performance;

import org.eclipse.jdt.testplugin.JavaProjectHelper;
import org.eclipse.test.performance.Dimension;
import org.eclipse.test.performance.PerformanceTestCaseJunit4;

import org.eclipse.swt.widgets.Shell;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;

import org.eclipse.core.resources.ResourcesPlugin;

import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import org.eclipse.jdt.internal.ui.JavaPlugin;

public class JdtPerformanceTestCaseCommon extends PerformanceTestCaseJunit4 {

	protected void joinBackgroudActivities() throws CoreException {
		// Join Building
		boolean interrupted= true;
		while (interrupted) {
			try {
				Job.getJobManager().join(ResourcesPlugin.FAMILY_AUTO_BUILD, null);
				interrupted= false;
			} catch (InterruptedException e) {
				interrupted= true;
			}
		}
		// Join indexing
		JavaProjectHelper.performDummySearch();
		// Join jobs. Maximal wait 1 minute before all jobs have completed
		if(!joinJobs(0, 1 * 60 * 1000, 500)) {
			JavaPlugin.logErrorMessage("Performance test " + tn.getMethodName() + " started with running background activity");
		}
	}

	private static boolean joinJobs(long minTime, long maxTime, long intervalTime) {
		long startTime= System.currentTimeMillis() + minTime;
		runEventQueue();
		while (System.currentTimeMillis() < startTime)
			runEventQueue(intervalTime);

		long endTime= maxTime > 0  && maxTime < Long.MAX_VALUE ? System.currentTimeMillis() + maxTime : Long.MAX_VALUE;
		boolean calm= allJobsQuiet();
		while (!calm && System.currentTimeMillis() < endTime) {
			runEventQueue(intervalTime);
			calm= allJobsQuiet();
		}
		return calm;
	}

	private static void sleep(int intervalTime) {
		try {
			Thread.sleep(intervalTime);
		} catch (InterruptedException e) {
		}
	}

	private static boolean allJobsQuiet() {
		IJobManager jobManager= Job.getJobManager();
		for (Job job : jobManager.find(null)) {
			int state= job.getState();
			if (state == Job.RUNNING || state == Job.WAITING)
				return false;
		}
		return true;
	}

	private static void runEventQueue() {
		IWorkbenchWindow window= PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (window != null)
			runEventQueue(window.getShell());
	}

	private static void runEventQueue(Shell shell) {
		while (shell.getDisplay().readAndDispatch()) {
			// do nothing
		}
	}

	private static void runEventQueue(long minTime) {
		long nextCheck= System.currentTimeMillis() + minTime;
		while (System.currentTimeMillis() < nextCheck) {
			runEventQueue();
			sleep(1);
		}
	}

	protected void finishMeasurements() {
		stopMeasuring();
		commitMeasurements();
		assertPerformanceInRelativeBand(Dimension.ELAPSED_PROCESS, -100, +10);
	}
}
