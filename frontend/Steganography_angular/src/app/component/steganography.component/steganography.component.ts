import { Component } from '@angular/core';
import { SteganographyService } from '../../steganography/steganography.service';
import { FormsModule } from '@angular/forms';
import { NgIf } from '@angular/common';
@Component({
  selector: 'app-steganography.component',
  imports: [FormsModule, NgIf],
  templateUrl: './steganography.component.html',
  styleUrl: './steganography.component.css',
})
export class SteganographyComponent {
  selectedfile!:File;
  secretText ='';
  result = '';
  loading = false;
  constructor(private stegoservice:SteganographyService) {}
  onfileselected(event:any){
    this.selectedfile = event.target.files[0];
  }
  encrypt(){
    if (!this.selectedfile || !this.secretText) {
  alert('Image and text required');
  return;
}

    this.loading = true;
    this.stegoservice.encrypt(this.selectedfile,this.secretText).subscribe((res:any)=>{
      this.result = res.message || 'Encryption successful';
      this.loading = false;
    },
    (err:any)=>{
      alert('Error during encryption');
      this.loading = false;
    }
    );
  }
  
  decrypt(){
    if(!this.selectedfile){
      alert('Image required');
      return;
    }
    this.loading = true;
    this.stegoservice.decrypt(this.selectedfile).subscribe((res:string)=>{
      this.result = res;
      this.loading = false;
    },
    (err:any)=>{
      alert('Error during decryption');
      this.loading = false;
    }
    );
  }
} 