package com.example.student_arrangement.controller;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.example.student_arrangement.common.Result;
import com.example.student_arrangement.entity.Game;
import com.example.student_arrangement.entity.Piece;
import com.example.student_arrangement.mapper.GameMapper;
import com.example.student_arrangement.mapper.PieceMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin


@RequestMapping(value = "/piece")
public class PieceController {
    @Resource
    PieceMapper pieceMapper;
    @Resource
    GameMapper gameMapper;

    @RequestMapping(value = "/addOnePiece",method = RequestMethod.POST)
    public Result<?> addOnePiece(@RequestBody Piece piece){
        Game theGame = gameMapper.selectByPlayer1OR2(piece.getPlayer());
        pieceMapper.insertPiece(theGame.getGameId(),piece.getPlayer(),piece.getpX(),piece.getpY());
        return Result.success();
    }

    @RequestMapping(value = "/getAllPiece",method = RequestMethod.POST)
    public Piece[] getAllPiece(@RequestBody Piece piece){
        if (gameMapper.selectByPlayer1OR2(piece.getPlayer())!=null)
            return pieceMapper.selectByGameId(gameMapper.selectByPlayer1OR2(piece.getPlayer()).getGameId());
        else return null;
    }

    @RequestMapping(value = "/changePiece",method = RequestMethod.POST)
    public Result<?> changePiece(@RequestBody Piece piece){//x是原值，y是改后值
        if (pieceMapper.selectByPxy(piece.getpX(),piece.getpX())==null)return Result.success();
        pieceMapper.changePieceById(piece.getpY(),piece.getpY(),
                pieceMapper.selectByPxy(piece.getpX(),piece.getpX()).getPieceId());

        return Result.success();
    }

    @RequestMapping(value = "/deletePiece",method = RequestMethod.POST)
    public Result<?> deletePiece(@RequestBody Piece piece){//x是原值，y是改后值
        pieceMapper.deleteByPosition(piece.getpX(),piece.getpY());
        return Result.success();
    }

    @RequestMapping(value = "/deleteAllPieces",method = RequestMethod.POST)
    public Result<?> deleteAllPieces(@RequestBody Piece piece){
        pieceMapper.deleteAllPieceById(gameMapper.selectByPlayer1OR2(piece.getPlayer()).getGameId());
        return Result.success();
    }

    @RequestMapping(value = "/judgeIfFiveWin",method = RequestMethod.POST)
    public Piece[] judgeIfFiveWin(@RequestBody Piece piece){

        //横向
        Piece[] xPiece = pieceMapper.selectByPxAndPlayer(piece.getpX(),piece.getPlayer());
        if (xPiece.length>=5){
            xPiece = binSortY(xPiece);
            int[] xArr = new int[xPiece.length];
            for (int i=0;i<xPiece.length;i++)
                xArr[i] = xPiece[i].getpY();
            if(ifHaveContinueFive(xArr,0)) {
                Piece[] result = new Piece[5];
                for (int j=0;j<5;j++)result[j] = xPiece[begin++];
                flag = 1;
                begin = -1;
                return result;
            }
        }



       //纵向
        Piece[] yPiece = pieceMapper.selectByPyAndPlayer(piece.getpY(),piece.getPlayer());
        if (yPiece.length>=5){
            Piece [] temp = binSortX(yPiece);
            for (int i=0;i<temp.length;i++)yPiece[i] = temp[i];
            int[] yArr = new int[yPiece.length];
            for (int i=0;i<yPiece.length;i++)
                yArr[i] = yPiece[i].getpX();
            if(ifHaveContinueFive(yArr,0)) {
                Piece[] result = new Piece[5];
                for (int j=0;j<5;j++)result[j] = yPiece[begin++];
                flag = 1;
                begin = -1;
                return result;
            }
        }



        //斜率为-1
        int kX = piece.getpX();
        int kY = piece.getpY();
        Piece positiveKPieceTemp[] = new Piece[15];
        int ind1 = 0;
        for (;kX>0&&kY>0;kX--,kY--){
            Piece thePiece = pieceMapper.selectByPxyAndPlayer(kX,kY,piece.getPlayer());
            if (thePiece!=null)positiveKPieceTemp[ind1++] = thePiece;
        }

        kX = piece.getpX()+1;
        kY = piece.getpY()+1;
        for (;kX<=15&&kY<=15;kX++,kY++){
            Piece thePiece = pieceMapper.selectByPxyAndPlayer(kX,kY,piece.getPlayer());
            if (thePiece!=null)positiveKPieceTemp[ind1++] = thePiece;
        }

        Piece positiveKPiece[] = new Piece[ind1];
        for (int i=0;i<ind1;i++)positiveKPiece[i] = positiveKPieceTemp[i];
        if (ind1>=5){

            Piece temp[] = binSortY(positiveKPiece);
            int [] kpArrX = new int[ind1];
            for (int i=0;i<ind1;i++){
                kpArrX[i] = temp[i].getpX();
            }
            if(ifHaveContinueFive(kpArrX,0)) {
                Piece[] result = new Piece[5];
                for (int j=0;j<5;j++)
                    result[j] = temp[begin++];
                flag = 1;
                begin = -1;
                return result;
            }
        }


        //斜率为1
        kX = piece.getpX();
        kY = piece.getpY();
        Piece negativeKPieceTemp[] = new Piece[15];
        int ind2 = 0;
        for (;kX>0&&kY>0;kX--,kY++){
            Piece thePiece = pieceMapper.selectByPxyAndPlayer(kX,kY,piece.getPlayer());
            if (thePiece!=null)negativeKPieceTemp[ind2++] = thePiece;
        }

        kX = piece.getpX()+1;
        kY = piece.getpY()-1;
        for (;kX<=15&&kY<=15;kX++,kY--){
            Piece thePiece = pieceMapper.selectByPxyAndPlayer(kX,kY,piece.getPlayer());
            if (thePiece!=null)negativeKPieceTemp[ind2++] = thePiece;
        }

        Piece negativeKPiece[] = new Piece[ind2];
        for (int i=0;i<ind2;i++)negativeKPiece[i] = negativeKPieceTemp[i];
        if (ind2>=5){


            Piece temp[] = binSortY(negativeKPiece);
            int [] knArrY = new int[ind2];
            for (int i=0;i<ind2;i++){
                knArrY[i] = temp[i].getpY();

            }
            if(ifHaveContinueFive(knArrY,0)){
                Piece[] result = new Piece[5];
                for (int j=0;j<5;j++){
                    result[j] = temp[begin++];
                }

                flag = 1;
                begin = -1;
                return result;
            }
        }


        return null;
    }

    int flag = 1;
    int begin = -1;
    public boolean ifHaveContinueFive(int []arr,int index){
        if (flag==5){
            return true;
        }
        else if (index==arr.length-1)return false;
        else {
            if (arr[index++]+1 == arr[index]){
                if (begin==-1)begin = index-1;
                flag++;
                return ifHaveContinueFive(arr,index);
            }else return ifHaveContinueFive(arr,index);
        }
    }

    public Piece[] binSortX(Piece[] arr){
        Piece[] bin = new Piece[15];
        for (int i=0;i<15;i++)bin[i] = null;
        for (int i=0;i<arr.length;i++)
            bin[arr[i].getpX()] = arr[i];
        Piece[] result = new Piece[arr.length];
        int ind = 0;
        for (int i=0;i<15;i++){
            if (bin[i]!=null)result[ind++] = bin[i];
        }
        return result;
    }

    public Piece[] binSortY(Piece[] arr){
        Piece[] bin = new Piece[15];
        for (int i=0;i<15;i++)bin[i] = null;
        for (int i=0;i<arr.length;i++)
            bin[arr[i].getpY()] = arr[i];
        Piece[] result = new Piece[arr.length];
        int ind = 0;
        for (int i=0;i<15;i++){
            if (bin[i]!=null)result[ind++] = bin[i];
        }
        return result;
    }




















}
