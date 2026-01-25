import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
@Injectable({
  providedIn: 'root',
})
export class SteganographyService {
  private apiUrl = 'http://localhost:8080/api/steganography';

  constructor(private http: HttpClient) {}

  encrypt(text: string, image: File) {
    const formData = new FormData();
    formData.append('text', text);
    formData.append('image', image);

    return this.http.post(
      `${this.apiUrl}/encrypt-download`,
      formData,
      { responseType: 'blob' }   // for download
    );
  }

  decrypt(image: File) {
    const formData = new FormData();
    formData.append('image', image);

    return this.http.post(
      `${this.apiUrl}/decrypt`,
      formData,
      { responseType: 'text' }   // returns string
    );
  }

}
