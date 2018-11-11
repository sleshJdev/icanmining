import {NgModule} from "@angular/core";
import {IntoComponent} from "./landing/intro.component";
import {HomeComponent} from "./home.component";
import {FooterComponent} from "./footer/footer.component";
import {SupportComponent} from "./support/support.component";
import {CommonModule} from "@angular/common";
import {BenefitsComponent} from "./landing/benefits.component";
import {IcanminingComponent} from "./landing/icanmining.component";
import {ProfitComponent} from "./landing/profit.component";
import {UserResponsibilitiesComponent} from "./landing/user-responsibilities.component";
import {MatButtonModule} from "@angular/material";
import {CloudMiningComponent} from "./landing/cloud-mining.component";

@NgModule({
  declarations: [
    HomeComponent, IntoComponent,
    BenefitsComponent, IcanminingComponent,
    ProfitComponent, UserResponsibilitiesComponent,
    CloudMiningComponent,
    FooterComponent, SupportComponent
  ],
  imports: [
    CommonModule,
    MatButtonModule
  ]
})
export class HomeModule {
}
