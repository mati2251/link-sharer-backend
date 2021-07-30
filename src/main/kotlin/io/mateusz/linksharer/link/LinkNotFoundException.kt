package io.mateusz.linksharer.link

import java.lang.RuntimeException

class LinkNotFoundException(id: Long) : RuntimeException("Could not find link $id")