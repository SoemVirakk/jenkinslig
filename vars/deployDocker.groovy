def call(Map config = [:]) {
    def image = config.get('image', 'my-default-image')
    def registry = config.get('registry', 'my-default-registry')
    def tag = config.get('tag', 'latest')
    def containerPort = config.get('containerPort', '8080')
    def hostPort = config.get('hostPort', '8080')
    def containerName = config.get('containerName', 'my-container')

    try {
        // Deploy Docker container using the given config parameters
        sh "docker run -d -p ${hostPort}:${containerPort} --name ${containerName} ${registry}/${image}:${tag}"
    } catch (Exception e) {
        echo "Error: ${e.message}"
        throw e
    }
}