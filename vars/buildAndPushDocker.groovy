// buildAndPushDocker.groovy

def call(imageName, dockerTag, credentialsId) {
    def fullImageName = "${imageName}:${dockerTag}"

    echo "Building Docker image with tag: ${fullImageName}"
    sh "docker build -t ${fullImageName} ."
    
    withCredentials([usernamePassword(credentialsId: credentialsId, usernameVariable: 'USER', passwordVariable: 'PASS')]) {
        sh 'docker login -u $USER -p $PASS'
        sh "docker push ${fullImageName}"
        sh 'docker logout'
    }
}
