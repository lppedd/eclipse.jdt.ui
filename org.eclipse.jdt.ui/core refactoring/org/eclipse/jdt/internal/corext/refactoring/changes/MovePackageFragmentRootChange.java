/*******************************************************************************
 * Copyright (c) 2002 International Business Machines Corp. and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v0.5 
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v05.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 ******************************************************************************/
package org.eclipse.jdt.internal.corext.refactoring.changes;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;

import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;

import org.eclipse.jdt.internal.corext.refactoring.RefactoringCoreMessages;
import org.eclipse.jdt.internal.corext.refactoring.reorg.IDeepCopyQuery;
import org.eclipse.jdt.internal.corext.refactoring.reorg.IPackageFragmentRootManipulationQuery;

public class MovePackageFragmentRootChange extends PackageFragmentRootReorgChange {

	public MovePackageFragmentRootChange(IPackageFragmentRoot root, IProject destination, IPackageFragmentRootManipulationQuery updateClasspathQuery, IDeepCopyQuery deepCopyQuery) {
		super(root, destination, null, updateClasspathQuery, deepCopyQuery);
	}

	/*
	 * @see org.eclipse.jdt.internal.corext.refactoring.changes.PackageFragmentRootReorgChange#doPerform(org.eclipse.core.runtime.IPath, org.eclipse.core.runtime.IProgressMonitor)
	 */
	protected void doPerform(IPath destinationPath, IProgressMonitor pm) throws JavaModelException {
		getRoot().move(destinationPath, getResourceUpdateFlags(), getUpdateModelFlags(false), null, pm);
	}

	/*
	 * @see org.eclipse.jdt.internal.corext.refactoring.base.IChange#getName()
	 */
	public String getName() {
		String[] keys= {getRoot().getElementName(), getDestinationProject().getName()};
		return RefactoringCoreMessages.getFormattedString("MovePackageFragmentRootChange.move", keys); //$NON-NLS-1$
	}
}