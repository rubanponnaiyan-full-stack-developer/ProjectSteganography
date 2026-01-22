import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
@Injectable({
  providedIn: 'root',
})
export class SteganographyService {
  private baseUrl = "http://localhost:8080/api/steganography";
  constructor(private http: HttpClient) {}
  encrypt(file: File, text: string) {
    const formData = new FormData();
    formData.append('image', file);
    formData.append('text', text);
    return this.http.post(`${this.baseUrl}/encrypt`, formData,
    {responseType: 'json' }
  );
  }
  decrypt(file: File) {
    const formData = new FormData();
    formData.append('image', file);
    return this.http.post(`${this.baseUrl}/decrypt`, formData,
    {responseType: 'text' }
  );
  }

  test(){
    return this .http.get(`${this.baseUrl}/test`,
    {responseType: 'text' }
  );
  }
}
