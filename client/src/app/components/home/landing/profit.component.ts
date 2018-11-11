import {Component} from "@angular/core";

@Component({
  selector: 'app-home-profit',
  styles: [`
    section {
      display: flex;
      justify-content: center;
      padding: 0 15%;
    }

    .big-illustration {
      width: 496px;
      height: 344px;
    }

    .big-illustration img {
      object-fit: contain;
    }
    
    .profit {
      width: 480px;
    }
    
    .profit__intro {
      height: 64px;
      font-size: 24px;
      text-align: left;
      line-height: 1.33;
      color: var(--color-black);
    }
    
    .profit__proof {
      height: 120px;
      font-size: 14px;
      line-height: 1.71;
      text-align: left;
      color: var(--color-black);
    }
    
    .profit__start-earning {
      width: 288px;
      height: 58px;
      margin-top: 36px;
      border-radius: 29px;
      border: 1px solid var(--color-primary-light);
    }
  `],
  template: `
    <section>
      <div class="big-illustration">
        <img src="../../../../assets/images/big-illustration.svg">
      </div>
      <div class="profit">
        <p class="profit__intro">Ваша прибыль теперь зависит от мощности видеокарты вашего ПК</p>
        <p class="profit__proof">Нет скрытых установок, все действия производятся только с вашего разрешения. Все заботы и
          оплаты по конвертации и переводам мы берем на себя. Мы доставим ваши заработанные средства на ваш банковский
          счет в валюте страны проживания. И, как результат, вы получаете 70% от заработанных средств.</p>
        <button class="profit__start-earning" type="button" mat-button>
          Начать зарабатывать
        </button>
      </div>
    </section>`
})
export class ProfitComponent {
}
