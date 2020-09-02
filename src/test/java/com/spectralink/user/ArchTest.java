package com.spectralink.user;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.spectralink.user");

        noClasses()
            .that()
            .resideInAnyPackage("com.spectralink.user.service..")
            .or()
            .resideInAnyPackage("com.spectralink.user.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.spectralink.user.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
