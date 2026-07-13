import { HttpInterceptorFn } from '@angular/common/http';
import { environment } from '@env/environment';

export const apiInterceptor: HttpInterceptorFn = (req, next) => {

  if (req.url.startsWith('http://') || req.url.startsWith('https://') || req.url.startsWith('assets/')) {
    return next(req);
  }

  const baseUrl = environment.apiUrl.replace(/\/$/, '');
  const cleanUrl = req.url.startsWith('/') ? req.url : `/${req.url}`;

  const apiReq = req.clone({
    url: `${baseUrl}${cleanUrl}`
  });

  return next(apiReq);
};
