/*******************************************************************************
 * Copyright (c) 2023, Red Hat, Inc. and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.jdt.internal.javac.dom;

import java.util.Arrays;
import java.util.Objects;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.IAnnotationBinding;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.IModuleBinding;
import org.eclipse.jdt.core.dom.IPackageBinding;
import org.eclipse.jdt.core.dom.JavacBindingResolver;

import com.sun.tools.javac.code.Symbol.PackageSymbol;

public abstract class JavacPackageBinding implements IPackageBinding {

	public final PackageSymbol packageSymbol;
	final JavacBindingResolver resolver;

	public JavacPackageBinding(PackageSymbol packge, JavacBindingResolver resolver) {
		this.packageSymbol = packge;
		this.resolver = resolver;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof JavacPackageBinding other
				&& Objects.equals(this.resolver, other.resolver)
				&& Objects.equals(this.packageSymbol, other.packageSymbol);
	}
	@Override
	public int hashCode() {
		return Objects.hash(this.resolver, this.packageSymbol);
	}

	@Override
	public IAnnotationBinding[] getAnnotations() {
		return this.packageSymbol.getAnnotationMirrors().stream()
				.map(am -> this.resolver.bindings.getAnnotationBinding(am, this))
				.toArray(IAnnotationBinding[]::new);
	}

	@Override
	public int getKind() {
		return PACKAGE;
	}

	@Override
	public int getModifiers() {
		return JavacMethodBinding.toInt(this.packageSymbol.getModifiers());
	}

	@Override
	public boolean isDeprecated() {
		return this.packageSymbol.isDeprecated();
	}

	@Override
	public boolean isRecovered() {
		return false;
	}

	@Override
	public boolean isSynthetic() {
		return false;
	}

	@Override
	public IJavaElement getJavaElement() {
		System.err.println("Hardocded binding->IJavaElement to 1st package");
		if (this.resolver.javaProject == null) {
			return null;
		}
		try {
			IJavaElement ret = Arrays.stream(this.resolver.javaProject.getAllPackageFragmentRoots())
				.map(root -> root.getPackageFragment(this.packageSymbol.getQualifiedName().toString()))
				.filter(Objects::nonNull)
				.filter(IPackageFragment::exists)
				.findFirst()
				.orElse(null);

			// TODO need to make sure the package is accessible in the module. :|
			return ret;
		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public IModuleBinding getModule() {
		return this.resolver.bindings.getModuleBinding(this.packageSymbol.modle);
	}

	@Override
	public String getKey() {
		if (this.packageSymbol.isUnnamed()) {
			return "";
		}
		return this.packageSymbol.getQualifiedName().toString().replace('.', '/');
	}

	@Override
	public boolean isEqualTo(IBinding binding) {
		return binding instanceof JavacPackageBinding other && //
			Objects.equals(this.packageSymbol, other.packageSymbol) && //
			Objects.equals(this.resolver, other.resolver);
	}

	@Override
	public String getName() {
		return isUnnamed() ? "" : this.packageSymbol.getQualifiedName().toString(); //$NON-NLS-1$
	}

	@Override
	public boolean isUnnamed() {
		return this.packageSymbol.isUnnamed();
	}

	@Override
	public String[] getNameComponents() {
		return isUnnamed()? new String[0] : this.packageSymbol.getQualifiedName().toString().split("\\."); //$NON-NLS-1$
	}

	@Override
	public String toString() {
		return "package " + getName();
	}
}
