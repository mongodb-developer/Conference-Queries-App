import SwiftUI
import shared

struct ContentView: View {
	
    
    @State var queries = [QueryInfo]()
    @State var query:String = ""

    var repo = RealmRepo()
    
	var body: some View {
        VStack{

            
            TextField("Enter Query", text: $query)
                .multilineTextAlignment(.center)
                .textFieldStyle(.roundedBorder)
                .padding(EdgeInsets(top: 0, leading: 20, bottom: 0, trailing: 20))
                
            
            Button("Save"){
                save(txt: query)
            }
            
        }
        .navigationBarBackButtonHidden(true)
        .navigationBarTitle("Mongo Dub Event",displayMode: .inline)
    }
    
    func save(txt: String){
        repo.saveInfo(query: txt) { _ in
            print("Error")
        }
    }
    
}



struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
