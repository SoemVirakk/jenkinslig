def call(Map config = [:]) {
    def image = config.get('image', 'my-default-image')
    def registry = config.get('registry', 'my-default-registry')
    def tag = config.get('tag', 'latest')
    def credentialsId = config.get('credentialsId', 'dockerhub-credentials')

    // Write Dockerfile to the workspace
    def dockerfileContent = libraryResource 'apiMedicalClinic.dockerfile'
    writeFile file: 'Dockerfile', text: dockerfileContent

    // Initial build using the radomkhoem/medical-clinic-docker:latest tag
    withCredentials([usernamePassword(credentialsId: credentialsId, passwordVariable: 'PASSWORD', usernameVariable: 'USERNAME')]) {
        sh """
            docker login -u ${USERNAME} -p ${PASSWORD}
            docker build -t ${registry}/${image}:${tag} -f Dockerfile .
            docker push ${registry}/${image}:${tag}
        """
    }
}
