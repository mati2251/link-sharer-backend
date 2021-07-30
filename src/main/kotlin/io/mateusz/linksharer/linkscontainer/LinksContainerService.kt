package io.mateusz.linksharer.linkscontainer

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LinksContainerService {

    @Autowired
    private lateinit var linksContainerRepository: LinksContainerRepository

    fun getLinksContainers(): MutableIterable<LinksContainer> {
        return linksContainerRepository.findAll()
    }

    fun getLinksContainer(id: Long): LinksContainer {
        return linksContainerRepository.findById(id).orElseThrow { LinksContainerNotFoundException(id) }
    }

    fun createLinksContainer(linksContainer: LinksContainer): LinksContainer {
        return linksContainerRepository.save(linksContainer)
    }
}