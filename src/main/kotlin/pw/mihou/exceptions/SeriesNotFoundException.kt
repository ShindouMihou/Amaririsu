package pw.mihou.exceptions

class SeriesNotFoundException(link: String):
    RuntimeException("The series ($link) cannot be accessed because there is no series linked with the link.")