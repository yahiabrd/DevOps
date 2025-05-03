import { provideHttpClient } from '@angular/common/http';
import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { AppComponent } from './app/main-component/app.component';

bootstrapApplication(AppComponent, {
  providers: [
    provideHttpClient(),
    ...(appConfig.providers || [])
  ]
}).catch((err) => console.error(err));