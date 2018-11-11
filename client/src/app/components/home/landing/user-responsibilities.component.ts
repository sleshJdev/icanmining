import {Component} from "@angular/core";

@Component({
  selector: 'app-home-user-responsibilities',
  styles: [`
    section {
      display: flex;
      justify-content: space-between;
      padding: 92px 15% 102px 15%;
      background-image: linear-gradient(99deg, #6bd0f4, #83f1c0);
    }

    .info {
      width: 352px;
      margin-right: 160px;
    }
    
    .info__intro {
      font-size: 24px;
      line-height: 1.33;
      text-align: left;
      color: var(--color-black);
    }
    
    .info__proof {
      font-size: 14px;
      line-height: 1.71;
      text-align: left;
      color: var(--color-black);
    }
    
    .info__install-mining {
      width: 288px;
      height: 58px;
      border-radius: 29px;
      border: transparent;
      background-color: #ffffff;
      box-shadow: 0 10px 15px 0 rgba(0, 0, 0, 0.1);
    }
    
    .miners {
      width: 424px;
      height: 304px;
    }
    
    .miners img {
      object-fit: contain;
    }
  `],
  template: `
    <section>
      <div class="info">
        <p class="info__intro">Что остается делать Вам?</p>
        <p class="info__proof">Прочитать правила, установить программу и заниматься своими делами. Иногда наблюдать, за
          счетчиком монитора за вашими деньгами.</p>
        <button class="info__install-mining" type="button" mat-button>Установить майнер</button>
      </div>
      <div class="miners">
        <img src="../../../../assets/images/miners.svg">
      </div>
    </section>`
})
export class UserResponsibilitiesComponent {
}
