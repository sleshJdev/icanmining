import {Component} from "@angular/core";

@Component({
  selector: 'app-cloud-mining',
  styles: [`
    section {
      padding: 112px 15% 147px 15%;
    }
    
    .header {
      font-size: 32px;
      line-height: 1.25;
      text-align: center;
      margin-bottom: 128px;
      color: var(--color-black);
    }
    
    .benefits {
      display: flex;
      justify-content: space-between;
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
      <p class="header">Преимущества облачного майнинга:</p>
      <div class="benefits">
        <div class="benefit">
          <div class="benefit__logo">
            <img src="../../../../assets/images/tools.svg"/>
          </div>
          <p>
            <em>
              Вам не придется со­би­рать и об­слу­жи­вать соб­ствен­ные уста­нов­ки, раз­би­рать­ся с на­строй­ка­ми ПО и непред­ви­ден­ны­ми про­бле­ма­ми.
            </em>
          </p>
        </div>
        <div class="benefit">
          <div class="benefit__logo">
            <img src="../../../../assets/images/graph.svg"/>
          </div>
          <p>
            <em>
              Выгодное использование персональных вычислительных мощностей для стабильных заработков.
            </em>
          </p>
        </div>
        <div class="benefit">
          <div class="benefit__logo">
            <img src="../../../../assets/images/bitcoin-green-logo.svg"/>
          </div>
          <p>
            <em>
              Возможность участвовать в отрасли, переживающей бум, без особых усилий.
            </em>
          </p>
        </div>
      </div>
    </section>
  `
})
export class CloudMiningComponent {
}
