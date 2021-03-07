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
                .layer("PortAdapter")
                .definedBy("com.github.gatoke.offers.port.adapter..")

                .layer("Application")
                .definedBy("com.github.gatoke.offers.application..")

                .layer("Domain")
                .definedBy("com.github.gatoke.offers.domain..")

                .whereLayer("PortAdapter").mayNotBeAccessedByAnyLayer()
                .whereLayer("Application").mayOnlyBeAccessedByLayers("PortAdapter")
                .whereLayer("Domain").mayOnlyBeAccessedByLayers("Application", "PortAdapter")

        then:
        architectureRules.check(importedClasses)
    }
}
