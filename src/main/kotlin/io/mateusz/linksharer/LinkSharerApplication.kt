package io.mateusz.linksharer

import io.mateusz.linksharer.link.Link
import io.mateusz.linksharer.link.LinkRepository
import io.mateusz.linksharer.linkscontainer.LinksContainer
import io.mateusz.linksharer.linkscontainer.LinksContainerRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class LinkSharerApplication {
    @Bean
    fun mappingDemo(
        linkRepository: LinkRepository,
        linksContainerRepository: LinksContainerRepository
    ): CommandLineRunner {
        return CommandLineRunner {
            val link = linksContainerRepository.save(LinksContainer("Container 1", "description"))
            linkRepository.save(Link("Facebook", "http..", link))
            linkRepository.save(Link("Twitter", "http..", link))
        }
    }
}

fun main(args: Array<String>) {
    runApplication<LinkSharerApplication>(*args)
}


