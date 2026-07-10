import { TestBed } from '@angular/core/testing';
import { HttpClient, provideHttpClient, withInterceptors } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { apiInterceptor } from './api.interceptor';
import { environment } from '@env/environment';

describe('ApiInterceptor', () => {
  let httpClient: HttpClient;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        provideHttpClient(withInterceptors([apiInterceptor])),
        provideHttpClientTesting()
      ]
    });

    httpClient = TestBed.inject(HttpClient);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should append baseUrl to relative URLs', () => {
    httpClient.get('/api/v1/test').subscribe();

    const baseUrl = environment.apiUrl.replace(/\/$/, '');
    const req = httpMock.expectOne(`${baseUrl}/api/v1/test`);
    expect(req.request.url).toBe(`${baseUrl}/api/v1/test`);
  });

  it('should not modify absolute HTTP URLs', () => {
    const absoluteUrl = 'http://external-api.com/data';
    httpClient.get(absoluteUrl).subscribe();

    const req = httpMock.expectOne(absoluteUrl);
    expect(req.request.url).toBe(absoluteUrl);
  });

  it('should not modify absolute HTTPS URLs', () => {
    const absoluteUrl = 'https://external-api.com/data';
    httpClient.get(absoluteUrl).subscribe();

    const req = httpMock.expectOne(absoluteUrl);
    expect(req.request.url).toBe(absoluteUrl);
  });

  it('should not modify assets URLs', () => {
    httpClient.get('assets/config.json').subscribe();

    const req = httpMock.expectOne('assets/config.json');
    expect(req.request.url).toBe('assets/config.json');
  });

  it('should handle URLs without leading slash safely', () => {
    httpClient.get('api/v1/test').subscribe();

    const baseUrl = environment.apiUrl.replace(/\/$/, '');
    const req = httpMock.expectOne(`${baseUrl}/api/v1/test`);
    expect(req.request.url).toBe(`${baseUrl}/api/v1/test`);
  });
});
