import {Component, OnInit} from '@angular/core';
import {KeycloakService} from "keycloak-angular";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'frontend-angular';

  public profile: any;

  constructor(public keycloakService: KeycloakService) {
  }

  ngOnInit(): void {
    if (this.keycloakService.isLoggedIn()) {
      this.keycloakService.loadUserProfile().then(profile=> {
        this.profile=profile;
      });
    }
  }

  logout() {
    console.log("this is a logout ")
    this.keycloakService.logout("http://localhost:4200");
  }

  async login() {
    this.keycloakService.login({
      redirectUri: window.location.origin
    });
  }
}
