/*******************************************************************************
 * Copyright (c) 2000, 2003 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jdt.core;

/**
 * Common protocol for Java elements that support source code assist and code
 * resolve.
 * <p>
 * This interface is not intended to be implemented by clients.
 * </p>
 */
public interface ICodeAssist {

	/**
	 * Performs code completion at the given offset position in this compilation unit,
	 * reporting results to the given completion requestor. The <code>offset</code>
	 * is the 0-based index of the character, after which code assist is desired.
	 * An <code>offset</code> of -1 indicates to code assist at the beginning of this
	 * compilation unit.
	 * 
	 * @param offset the given offset position
	 * @param requestor the given completion requestor
	 *
	 * @exception JavaModelException if code assist could not be performed. Reasons include:<ul>
	 *  <li>This Java element does not exist (ELEMENT_DOES_NOT_EXIST)</li>
	 *  <li> The position specified is < -1 or is greater than this compilation unit's
	 *      source length (INDEX_OUT_OF_BOUNDS)
	 * </ul>
	 *
	 * @exception IllegalArgumentException if <code>requestor</code> is <code>null</code>
	 * @deprecated - use codeComplete(int, ICompletionRequestor) instead
	 * TODO: remove before 3.0
	 */
	void codeComplete(int offset, ICodeCompletionRequestor requestor)
		throws JavaModelException;
	/**
	 * Performs code completion at the given offset position in this compilation unit,
	 * reporting results to the given completion requestor. The <code>offset</code>
	 * is the 0-based index of the character, after which code assist is desired.
	 * An <code>offset</code> of -1 indicates to code assist at the beginning of this
	 * compilation unit.
	 *
	 * @param offset the given offset position
	 * @param requestor the given completion requestor
	 * @exception JavaModelException if code assist could not be performed. Reasons include:<ul>
	 *  <li>This Java element does not exist (ELEMENT_DOES_NOT_EXIST)</li>
	 *  <li> The position specified is < -1 or is greater than this compilation unit's
	 *      source length (INDEX_OUT_OF_BOUNDS)
	 * </ul>
	 *
	 * @exception IllegalArgumentException if <code>requestor</code> is <code>null</code>
	 * @since 2.0
 	 */
	void codeComplete(int offset, ICompletionRequestor requestor)
		throws JavaModelException;
	/**
	 * Performs code completion at the given offset position in this compilation unit,
	 * reporting results to the given completion requestor. The <code>offset</code>
	 * is the 0-based index of the character, after which code assist is desired.
	 * An <code>offset</code> of -1 indicates to code assist at the beginning of this
	 * compilation unit.
	 * It considers types in the working copies with the given owner first. In other words, 
	 * the owner's working copies will take precedence over their original compilation units
	 * in the workspace.
	 * <p>
	 * Note that if a working copy is empty, it will be as if the original compilation
	 * unit had been deleted.
	 * </p>
	 *
	 * @param offset the given offset position
	 * @param requestor the given completion requestor
	 * @param owner the owner of working copies that take precedence over their original compilation units
	 * @exception JavaModelException if code assist could not be performed. Reasons include:<ul>
	 *  <li>This Java element does not exist (ELEMENT_DOES_NOT_EXIST)</li>
	 *  <li> The position specified is < -1 or is greater than this compilation unit's
	 *      source length (INDEX_OUT_OF_BOUNDS)
	 * </ul>
	 *
	 * @exception IllegalArgumentException if <code>requestor</code> is <code>null</code>
	 * @since 3.0
	 */
	void codeComplete(int offset, ICompletionRequestor requestor, WorkingCopyOwner owner)
		throws JavaModelException;
	/**
	 * Performs code selection on the given selected text in this compilation unit,
	 * reporting results to the given selection requestor. The <code>offset</code>
	 * is the 0-based index of the first selected character. The <code>length</code> 
	 * is the number of selected characters.
	 * 
	 * @param offset the given offset position
	 * @param length the number of selected characters
	 *
	 * @exception JavaModelException if code resolve could not be performed. Reasons include:
	 *  <li>This Java element does not exist (ELEMENT_DOES_NOT_EXIST)</li>
	 *  <li> The range specified is not within this element's
	 *      source range (INDEX_OUT_OF_BOUNDS)
	 * </ul>
	 *
	 */
	IJavaElement[] codeSelect(int offset, int length) throws JavaModelException;
	/**
	 * Performs code selection on the given selected text in this compilation unit,
	 * reporting results to the given selection requestor. The <code>offset</code>
	 * is the 0-based index of the first selected character. The <code>length</code> 
	 * is the number of selected characters.
	 * It considers types in the working copies with the given owner first. In other words, 
	 * the owner's working copies will take precedence over their original compilation units
	 * in the workspace.
	 * <p>
	 * Note that if a working copy is empty, it will be as if the original compilation
	 * unit had been deleted.
	 * </p>
	 * 
	 * @param offset the given offset position
	 * @param length the number of selected characters
	 * @param owner the owner of working copies that take precedence over their original compilation units
	 *
	 * @exception JavaModelException if code resolve could not be performed. Reasons include:
	 *  <li>This Java element does not exist (ELEMENT_DOES_NOT_EXIST)</li>
	 *  <li> The range specified is not within this element's
	 *      source range (INDEX_OUT_OF_BOUNDS)
	 * </ul>
	 * @since 3.0
	 */
	IJavaElement[] codeSelect(int offset, int length, WorkingCopyOwner owner) throws JavaModelException;
}
