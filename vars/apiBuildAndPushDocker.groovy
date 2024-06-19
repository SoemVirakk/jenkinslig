def call() {
    def dockerfileContent = libraryResource 'apiMedicalClinic.dockerfile'
    writeFile file: 'Dockerfile', text: dockerfileContent
    sh 'docker build -t myapp:latest .'
}