package hr.softwaresauna.codechallenge.exception

class InvalidCharException(message: String) : RuntimeException(message) {
}

class MultipleEndsException : RuntimeException("Multiple ends!") {
}

class MultipleStartingPathsException : RuntimeException("Multiple starting paths!") {
}

class MultipleStartsException : RuntimeException("Multiple starts!") {
}

class NoEndException : RuntimeException("No end!") {
}

class NoStartException : RuntimeException("No start!") {
}

class NoStartingPathException : RuntimeException("No starting path exception!") {
}

class ForksException(message: String) : RuntimeException(message) {
}

class BrokenPathException(message: String) : RuntimeException(message) {
}

class FakeTurnException(message: String) : RuntimeException(message) {
}
