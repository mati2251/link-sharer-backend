package io.mateusz.linksharer.linkscontainer

import java.lang.RuntimeException

class LinksContainerNotFoundException(id: Long) : RuntimeException("Could not find order $id")