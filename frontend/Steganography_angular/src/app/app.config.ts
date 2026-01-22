import { ApplicationConfig} from '@angular/core';
import { provideHttpClient } from '@angular/common/http';
import { routes } from './app.routes';
import { importProvidersFrom } from '@angular/core';
import { provideRouter, RouterModule } from '@angular/router';
export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    { provide: 'BASE_API_URL', useValue: 'http://localhost:8080/api' },
    importProvidersFrom(
      RouterModule.forRoot(routes)
    ),
    provideHttpClient()
  ]
};
