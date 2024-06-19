def call() {
    def dockerfileContent = libraryResource 'medicalClinic.dockerfile'
    writeFile file: 'Dockerfile', text: dockerfileContent
    // sh 'docker build -t myapp:latest .'
}