application {
    title = 'ast-bug'
    startupGroups = ['astBug']
    autoShutdown = true
}
mvcGroups {
    // MVC Group for "astBug"
    'astBug' {
        model      = 'org.example.AstBugModel'
        view       = 'org.example.AstBugView'
        controller = 'org.example.AstBugController'
    }
}