package io.mateusz.linksharer.link

import io.mateusz.linksharer.linkscontainer.LinksContainer
import io.mateusz.linksharer.linkscontainer.LinksContainerNotFoundException
import io.mateusz.linksharer.linkscontainer.LinksContainerRepository
import io.mateusz.linksharer.linkscontainer.LinksContainerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class LinkService {

    @Autowired
    private lateinit var linkRepository: LinkRepository

    @Autowired
    private lateinit var linksContainerService: LinksContainerService


    fun getLinks(): MutableList<Link> {
        return linkRepository.findAll()
    }

    fun getLinksByContainerId(id: Long): List<Link> {
        val linksContainer = linksContainerService.getLinksContainer(id)
        return linkRepository.findByLinksContainer(linksContainer)
    }

    fun getLinkById(id: Long): Link {
        return linkRepository.findById(id).orElseThrow { LinkNotFoundException(id) }
    }

    fun createLink(link: Link, containerId: Long): Link {
        val linksContainer = linksContainerService.getLinksContainer(containerId)
        link.setLinksContainer(linksContainer)
        return linkRepository.save(link)
    }

    fun createLink(link: Link): Link {
        return linkRepository.save(link)
    }

    fun deleteLink(link: Link) {
        linkRepository.delete(link)
    }

    fun deleteLink(id: Long) {
        val link = this.getLinkById(id)
        this.deleteLink(link)
    }
}