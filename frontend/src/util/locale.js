export default (navigator.languages && navigator.languages[0])
               || navigator.language
               || navigator.userLanguage
               || 'es-CL';