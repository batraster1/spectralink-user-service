import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SpectralinkUserServiceTestModule } from '../../../test.module';
import { UserSettingsUpdateComponent } from 'app/entities/user-settings/user-settings-update.component';
import { UserSettingsService } from 'app/entities/user-settings/user-settings.service';
import { UserSettings } from 'app/shared/model/user-settings.model';

describe('Component Tests', () => {
  describe('UserSettings Management Update Component', () => {
    let comp: UserSettingsUpdateComponent;
    let fixture: ComponentFixture<UserSettingsUpdateComponent>;
    let service: UserSettingsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SpectralinkUserServiceTestModule],
        declarations: [UserSettingsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UserSettingsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserSettingsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserSettingsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserSettings('123');
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserSettings();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
