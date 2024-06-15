def call(Map config = [:]) {
    def image = config.get('image', 'my-default-image')
    def registry = config.get('registry', 'my-default-registry')
    def tag = config.get('tag', 'latest')
    def credentialsId = config.get('credentialsId', 'dockerhub-credentials')

    // Write Dockerfile content to the workspace
    def dockerfileContent = libraryResource 'resources/angular.dockerfile'
    writeFile file: 'Dockerfile', text: dockerfileContent

    // Access the username and password using the provided credentialsId
    withCredentials([usernamePassword(credentialsId: credentialsId, passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        // Perform Docker login, build, and push commands
        sh """
            docker login -u $USER -p $PASS
            docker build -t ${registry}/${image}:${tag} -f Dockerfile .
            docker push ${registry}/${image}:${tag}
        """
    }
}
