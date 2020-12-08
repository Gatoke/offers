package com.github.gatoke.offers

import com.tngtech.archunit.core.importer.ClassFileImporter
import spock.lang.Specification

import static com.tngtech.archunit.library.Architectures.layeredArchitecture

class ArchitectureTest extends Specification {

    def shouldVerifyCorrectnessOfProjectArchitecture() {
        given:

        def importedClasses = new ClassFileImporter().importPackages("com.github.gatoke.offers")

        when:
        def architectureRules = layeredArchitecture()
                .layer("Infrastructure")
                .definedBy("com.github.gatoke.offers.infrastructure..")

                .layer("PortAdapter")
                .definedBy("com.github.gatoke.offers.port.adapter..")

                .layer("Application")
                .definedBy("com.github.gatoke.offers.application..")

                .layer("Domain")
                .definedBy("com.github.gatoke.offers.domain..")

                .whereLayer("PortAdapter").mayOnlyBeAccessedByLayers("Infrastructure")
                .whereLayer("Application").mayOnlyBeAccessedByLayers("PortAdapter", "Infrastructure")
                .whereLayer("Domain").mayOnlyBeAccessedByLayers("Application", "PortAdapter", "Infrastructure")

        then:
        architectureRules.check(importedClasses)
    }
}
