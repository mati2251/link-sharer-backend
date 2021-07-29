package io.mateusz.linksharer.linkscontainer

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["api/v1/container"])
class LinksContainerController {

    @Autowired
    private lateinit var linksContainerService: LinksContainerService

    @GetMapping
    fun getLinksContainers(): MutableIterable<LinksContainer> {
        return linksContainerService.getLinksContainers()
    }
}