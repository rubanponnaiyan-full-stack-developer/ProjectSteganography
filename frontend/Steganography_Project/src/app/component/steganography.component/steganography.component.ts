import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SteganographyService } from '../../service/steganography.service';
@Component({
  selector: 'app-steganography.component',
  imports: [ CommonModule, FormsModule ],
  templateUrl: './steganography.component.html',
  styleUrl: './steganography.component.css',
})
export class SteganographyComponent {
  selectedFile!: File;
  secretText = '';
  result = '';
  loading = false;
  downloadUrl: string | null = null;
  decryptedTextDownloadUrl: string | null = null;

  constructor(private stegoService: SteganographyService) {}

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
  }

  encrypt() {
    if (!this.selectedFile) return alert('Select a file first');
    this.loading = true;

    this.stegoService.encrypt(this.secretText, this.selectedFile)
      .subscribe(blob => {
        this.loading = false;
        this.downloadUrl = URL.createObjectURL(blob); // download
      });
  }

  decrypt() {
    if (!this.selectedFile) return alert('Select a file first');
    this.loading = true;

    this.stegoService.decrypt(this.selectedFile)
      .subscribe(text => {
        this.loading = false;
        this.result = text;
        this.decryptedTextDownloadUrl = URL.createObjectURL(new Blob([text], { type: 'text/plain' })); // download

      });
  }
}

