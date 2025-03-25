import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { SwimSet } from './swimsets';

@Injectable({
  providedIn: 'root'
})
export class SwimSetService {

  constructor(
    public client: HttpClient
  ) { }


  public getSets(): SwimSet[] {
    let apiSets: SwimSet[] = [];
    this.client.get<SwimSet[]>("/swimsets").forEach(
      setList => setList.forEach(set => apiSets.push(set))
    );
    return apiSets
  }
}
