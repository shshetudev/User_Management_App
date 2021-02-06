import { Component, OnInit } from '@angular/core';
import * as routes from '../../../utils/global-routes';
@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  globalRoutes: any;
  constructor() { }

  ngOnInit(): void {
    this.globalRoutes = routes;
  }

}
