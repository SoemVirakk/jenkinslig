def call() {
    def dockerfileContent = libraryResource 'apiMedicalClinic.dockerfile'
    writeFile file: 'Dockerfile', text: dockerfileContent
    sh 'docker build -t radomkhoem/medical-clinic-docker:latest .'
}