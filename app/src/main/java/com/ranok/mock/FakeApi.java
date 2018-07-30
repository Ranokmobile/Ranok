package com.ranok.mock;

import com.ranok.network.NetApi;
import com.ranok.network.request.LoginRequest;

import io.reactivex.Single;

public class FakeApi  implements NetApi {
    @Override
    public Single<String> login(LoginRequest loginRequest) {
        return null;
    }
    //    @Override
//    public Single<List<NewsHeader>> getNewsList(long time) {
//        List<NewsHeader> list = new ArrayList<>();
//        final Random random = new Random();
//        Observable.range(1,10)
//                .map(integer -> new NewsHeader(random.nextInt(), randomUUID().toString(), "text", new Date(new Date().getTime() - Math.abs(random.nextInt())) ))
//                .toList()
//                .subscribe((Consumer<List<NewsHeader>>) list::addAll);
//
//        return Single.just(list).delay(2000, TimeUnit.MILLISECONDS);
//    }
//
//    @Override
//    public Single<NewsContent> getNews(long id) {
//
//        StringBuilder sb = new StringBuilder();
//        sb.append("News - \n");
//        sb.append(randomUUID().toString());
//        sb.append("\n");
//        sb.append(randomUUID().toString());
//        sb.append("\n");
//        sb.append(randomUUID().toString());
//
//        byte[] content;
//
//        try {
//            content = sb.toString().getBytes("UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//            content = new byte[0];
//        }
//
//        return Single.just(new NewsContent(id, content)).delay(2000, TimeUnit.MILLISECONDS);
//    }
}
