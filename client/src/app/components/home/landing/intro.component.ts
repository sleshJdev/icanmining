import {Component} from "@angular/core";

@Component({
  selector: 'app-home-intro',
  styles: [`
    section {
      margin: auto;
      color: var(--color-black);
    }
    
    .bitcoin-full-logo {
      width: 64px;
      height: 80px;
      margin: auto;
    }
    
    .bitcoin-full-logo img {
      object-fit: contain;
    }
    
    .motto {
      font-size: 40px;
      line-height: 0.8;
      text-align: center;
    }

    .intro {
      width: 565px;
      margin: auto;
      font-size: 18px;
      line-height: 1.78;
      text-align: center;
    }

    button.mat-button.start-earning {
      display: block;
      width: 288px;
      height: 58px;
      margin: 28px auto 0 auto;
      border: 1px solid var(--color-primary-light);
      text-transform: uppercase;
      border-radius: 29px;
      box-shadow: 0 10px 15px 0 rgba(0, 0, 0, 0.1);
    }
    
    .flow {
      display: flex;
      justify-content: space-between;
      margin-top: -300px;
    }
    
    .flow img {
      object-fit: contain;
      width: 100%;
    }
  `],
  template: `
    <section>
      <div class="bitcoin-full-logo">
        <img src="../../../../assets/images/bitcoin-green-logo.svg"/>
      </div>
      <p class="text--center">
        International Canadian mining corporation
      </p>
      <p class="motto">
        Быстро, Безопастно, Удобно!
      </p>
      <p class="intro">
        <em>
          Люди , которые не успели вложить в биткоин , часто называют это пузырем , те которые уже вложили , помалкивают
          ,
          вы в этот момент зарабатываете !
        </em>
      </p>
      <button class="start-earning" type="button" mat-button>
        Начать Зарабатывать
      </button>
      <div class="flow">
        <img src="../../../../assets/images/left-flow.svg"/>
        <img src="../../../../assets/images/right-flow.svg"/>
      </div>
    </section>`
})
export class IntoComponent {

}
