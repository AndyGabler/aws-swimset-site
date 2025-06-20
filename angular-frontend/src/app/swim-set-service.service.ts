import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { SwimSet } from './swimsets';
import { API_PREFIX } from '../environment';

@Injectable({
  providedIn: 'root'
})
export class SwimSetService {

  constructor(
    public client: HttpClient
  ) { }


  public getSets(): SwimSet[] {
    let apiSets: SwimSet[] = [];
    this.client.get<SwimSet[]>(API_PREFIX + "/swimsets").forEach(
      setList => setList.forEach(set => apiSets.push(set))
    );
    return apiSets
  }
}
