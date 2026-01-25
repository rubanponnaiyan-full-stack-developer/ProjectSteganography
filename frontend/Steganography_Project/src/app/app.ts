import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, CommonModule, FormsModule],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  [x: string]: any;
onFileSelected($event: Event) {
throw new Error('Method not implemented.');
}
encrypt() {
throw new Error('Method not implemented.');
}
decrypt() {
throw new Error('Method not implemented.');
}
  protected readonly title = signal('Steganography_Project');
secretText: any;
result: any;
downloadUrl: any;
loading: any;
}
