import { Routes } from '@angular/router';
import { SteganographyComponent } from './component/steganography.component/steganography.component';
export const routes: Routes = [
    { path: '', redirectTo: 'steganography', pathMatch: 'full' },
    { path: 'steganography', component: SteganographyComponent }
];
