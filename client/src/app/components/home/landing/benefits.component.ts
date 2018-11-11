import {Component} from "@angular/core";

@Component({
  selector: 'app-home-benefits',
  styles: [`
    section {
      display: flex;
      justify-content: space-between;
      padding: 0 15%;
    }
    
    .benefit {
      width: 224px;
      font-size: 14px;
      line-height: 1.71;
      text-align: center;
      color: var(--color-black);
    }
    
    .benefit__logo {
      width: 128px;
      height: 128px;
      margin: auto;
    }

    .benefit__logo img {
      object-fit: contain;
    }
  `],
  template: `
    <section>
      <div class="benefit">
        <div class="benefit__logo">
          <img src="../../../../assets/images/processor.svg">
        </div>
        <p>
          Мы решили предложить вам то, что никто не предложит. А именно, заработать на том, что у вас уже есть.
        </p>
      </div>
      <div class="benefit">
        <div class="benefit__logo">
          <img src="../../../../assets/images/rocket.svg">
        </div>
        <p>
          Раскройте потенциал своего компьютера, дайте ему заработать для вас деньги, посредством ICAN MINING.
        </p>
      </div>
      <div class="benefit">
        <div class="benefit__logo">
          <img src="../../../../assets/images/monitor.svg">
        </div>
        <p>
          Программа установит майнер криптовплюты на ваш персональный компьютер безо всяких платежей и взносов.
        </p>
      </div>
    </section>`
})
export class BenefitsComponent {

}
