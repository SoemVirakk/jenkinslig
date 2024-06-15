def call(String DOCKER_TAG, String ImageName) {
    def dockerfileContent = libraryResource 'angular.dockerfile'
    writeFile file: 'Dockerfile', text: dockerfileContent
    sh "docker build -t ${ImageName}:${DOCKER_TAG} ."
    sh "echo ${DOCKER_TAG}"
} 