import { Routes } from '@angular/router';
import { SteganographyComponent } from './component/steganography.component/steganography.component';
export const routes: Routes = [
   {
    path: '', 
    redirectTo: 'steganography.component', 
    pathMatch: 'full'
   } ,
   
  { path: 'steganography.component', 
    component: SteganographyComponent 
  }
];
