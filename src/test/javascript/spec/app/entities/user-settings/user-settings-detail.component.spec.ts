import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SpectralinkUserServiceTestModule } from '../../../test.module';
import { UserSettingsDetailComponent } from 'app/entities/user-settings/user-settings-detail.component';
import { UserSettings } from 'app/shared/model/user-settings.model';

describe('Component Tests', () => {
  describe('UserSettings Management Detail Component', () => {
    let comp: UserSettingsDetailComponent;
    let fixture: ComponentFixture<UserSettingsDetailComponent>;
    const route = ({ data: of({ userSettings: new UserSettings('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SpectralinkUserServiceTestModule],
        declarations: [UserSettingsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UserSettingsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserSettingsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userSettings on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userSettings).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
