import {Component} from "@angular/core";

@Component({
  selector: 'app-home-benefits',
  styles: [`
    section {
      display: flex;
      justify-content: space-around;
      padding: 0 15%;
    }
    .benefit {
      width: 15em;
    }
    .benefit picture {
      width: 15em;
    }
    
    img {
      width: 100%;
    }
  `],
  template: `
    <section>
      <div class="benefit">
        <picture>
          <img src="../../../../assets/images/miner.jpg">
        </picture>
        <p>
          Мы решили предложить вам то, что никто не предложит. А именно, заработать на том, что у вас уже есть.
        </p>
      </div>
      <div class="benefit">
        <picture>
          <img src="../../../../assets/images/miner.jpg">
        </picture>
        <p>
          Раскройте потенциал своего компьютера, дайте ему заработать для вас деньги, посредством ICAN MINING.
        </p>
      </div>
      <div class="benefit">
        <picture>
          <img src="../../../../assets/images/miner.jpg">
        </picture>
        <p>
          Программа установит майнер криптовплюты на ваш персональный компьютер безо всяких платежей и взносов.
        </p>
      </div>
    </section>`
})
export class BenefitsComponent {

}
