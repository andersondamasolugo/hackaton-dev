import { Component, inject } from '@angular/core';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { AuthService } from '../../core/auth/auth.service';

/**
 * Componente de login.
 * Formulario reactivo con usuario y contraseña.
 * Navega a /menu en login exitoso, muestra error genérico en fallo.
 */
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
  imports: [ReactiveFormsModule],
})
export class LoginComponent {
  private readonly fb = inject(FormBuilder);
  private readonly authService = inject(AuthService);
  private readonly router = inject(Router);

  /** Formulario reactivo de login. */
  loginForm = this.fb.nonNullable.group({
    username: ['', Validators.required],
    password: ['', Validators.required],
  });

  /** Mensaje de error mostrado al usuario. */
  errorMessage = '';

  /** Indica si hay una petición de login en curso. */
  isLoading = false;

  /** Envía las credenciales al backend para autenticación. */
  onSubmit(): void {
    if (this.loginForm.invalid) {
      return;
    }

    this.isLoading = true;
    this.errorMessage = '';

    const { username, password } = this.loginForm.getRawValue();

    this.authService.login({ username, password }).subscribe({
      next: () => {
        this.router.navigate(['/menu']);
      },
      error: () => {
        this.errorMessage = 'Credenciales inválidas';
        this.isLoading = false;
      },
    });
  }
}
