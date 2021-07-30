package io.mateusz.linksharer.link

import io.mateusz.linksharer.linkscontainer.LinksContainer
import io.mateusz.linksharer.linkscontainer.LinksContainerNotFoundException
import io.mateusz.linksharer.linkscontainer.LinksContainerRepository
import io.mateusz.linksharer.linkscontainer.LinksContainerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LinkService {

    @Autowired
    private lateinit var linkRepository: LinkRepository

    @Autowired
    private lateinit var linksContainerService: LinksContainerService

    @Autowired
    private lateinit var linksContainerRepository: LinksContainerRepository

    fun getLinks(): MutableList<Link> {
        return linkRepository.findAll()
    }

    fun getLinksByContainerId(id: Long): List<Link> {
        val linksContainer = linksContainerService.getLinksContainer(id)
        return linkRepository.findByLinksContainer(linksContainer)
    }

    fun getLinkById(id: Long): Link {
        return linkRepository.getById(id)
    }

    fun createLink(link: Link, containerId: Long): Link {
        val linksContainer = linksContainerRepository.findById(containerId)
            .orElseThrow { LinksContainerNotFoundException(containerId) }
        link.setLinksContainer(linksContainer)
        return linkRepository.save(link)

    }
}