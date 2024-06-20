def call(Map config = [:]) {
    def image = config.get('image', 'my-default-image')
    def registry = config.get('registry', 'my-default-registry')
    def tag = config.get('tag', 'latest')
    def credentialsId = config.get('credentialsId', 'dockerhub-credentials')

    try {
        // Write Dockerfile to workspace
        def dockerfileContent = libraryResource 'apiMedicalClinic.dockerfile'
        writeFile file: 'Dockerfile', text: dockerfileContent

        // Build Docker image
        sh "docker build -t ${registry}/${image}:${tag} ."

        // Authenticate and push Docker image
        withCredentials([usernamePassword(credentialsId: credentialsId, passwordVariable: 'PASSWORD', usernameVariable: 'USERNAME')]) {
            sh "docker login -u ${USERNAME} -p ${PASSWORD}"
            sh "docker push ${registry}/${image}:${tag}"
        }
    } catch (Exception e) {
        echo "Error: ${e.message}"
        throw e
    }
}
