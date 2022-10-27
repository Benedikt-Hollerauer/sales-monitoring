package error.repositoryError

enum RepositoryError:

    case NotFound
    case Failure(error: Throwable)